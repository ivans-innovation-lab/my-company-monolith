
This service requires 

- 'mysql'

You can create it manually on PWS, or by running script:

```
cf api https://api.run.pivotal.io
cf auth EMAIL PASSWORD

# Space Staging
cf target -o idugalic -s Stage
cf create-service cleardb spark mysql

# Space Production
cf t -s Prod
cf create-service cleardb spark mysql


```