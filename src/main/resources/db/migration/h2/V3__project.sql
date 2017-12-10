
DROP TABLE IF EXISTS `project`;
CREATE TABLE PROJECT(
    ID VARCHAR(255) NOT NULL,
    AGGREGATE_VERSION BIGINT,
    CATEGORY VARCHAR(255),
    DESCRIPTION VARCHAR(255),
    NAME VARCHAR(255),
    REPO_URL VARCHAR(255),
    SITE_URL VARCHAR(255),
    VERSION BIGINT,
    ACTIVE TINYINT
);
ALTER TABLE PROJECT ADD CONSTRAINT CONSTRAINT_1 PRIMARY KEY(ID);

