## Database only

- This command runs a preconfigured PostgreSQL database.
- This is primary used for backend developers using a database to test their Rest-Service.

### Database Configuration
- **user** : am
- **passsword** : am
- **database_name** : am
- **host** : localhost
- **port** : 5432

### PostgreSQL Data Files / Reinitialize Database

The data files are stored in sub directory **../pg_data**. This directory is once created on first run and installs the required tables, views and initializes the data. 

To rebuild the database, shut the database down and delete the **../pg_data** directory. On next start the database will be reinitialized again.

The folder **../db_init** is used for initialization of the database.

### Warning

The initialization will fail, if encoding of file **../db_init/01-init.sh** is not Unix like. Line endings have to be CR and not Windows like CRLF.

- Convert to Unix with command : dos2unix < 01-init.sh > 01-init.sh.ux (replace old file with new one)
- Force git not to autoconvert CR to CRLF : git config --global core.autocrlf true


### Start/Stop
- **start**: docker-compose up
- **stop**: docker-compose down


### Data Model

![Data Model](akros_ma_dyn.svg)