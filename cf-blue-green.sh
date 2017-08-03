#!/bin/bash

# Blue-green deployment script. Usage:
#
#   ./cf-blue-green <appname> <org> <space> <email> <password>

set -e
set -o pipefail
set -x


if [ $# -ne 5 ]; then
	echo "Usage:\n\n\t./cf-blue-green <appname> <org> <space> <email> <password>\n"
	exit 1
fi


BLUE=$1
GREEN="${BLUE}-B"
ORG=$2
SPACE=$3
EMAIL=$4
PASSWORD=$5

finally ()
{
  # we don't want to keep the sensitive information around
  rm $MANIFEST
}

on_fail () {
  finally
  echo "DEPLOY FAILED - you may need to check 'cf apps' and 'cf routes' and do manual cleanup"
}

# Auth
cf api https://api.run.pivotal.io
cf auth $EMAIL $CF_PASSWORD
cf target -o $ORG -s $SPACE

# pull the up-to-date manifest from the BLUE (existing) application
MANIFEST=$(mktemp -t "${BLUE}_manifest.XXXXXXXXXX")
cf create-app-manifest $BLUE -p $MANIFEST

# set up try/catch
# http://stackoverflow.com/a/185900/358804
trap on_fail ERR

DOMAIN=${B_DOMAIN:-$(cat $MANIFEST | grep domain: | awk '{print $2}')}

# create the GREEN application
cf push $GREEN -f $MANIFEST -n $GREEN
# ensure it starts
curl --fail -I "https://${GREEN}.${DOMAIN}"

# add the GREEN application to each BLUE route to be load-balanced
# TODO this output parsing seems a bit fragile...find a way to use more structured output
cf routes | tail -n +4 | grep $BLUE | awk '{print $3" -n "$2}' | xargs -n 3 cf map-route $GREEN

# cleanup
# TODO consider 'stop'-ing the BLUE instead of deleting it, so that depedencies are cached for next time
cf delete $BLUE -f
cf rename $GREEN $BLUE
cf delete-route $DOMAIN -n $GREEN -f
finally

echo "DONE"
