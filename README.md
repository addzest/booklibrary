# README #


* Summary of set up
need mysql, Intellij Idea (git, maven)
1)copy project throw git on your Intellij Idea.
2)Run ddl/init.sql on mysql db.
3) Change credentials to your mysql db in hibernate.cfg.xml (booklibrary-service/src/main/resources/)
4) maven clean, install
5) configure tomcat server (start page: localhost:<port>/index, artifact: booklibrary-webapp.war)
6) run tomcat

* Summary to use
login as "librarian":
username: admin
password: admin

users register: all with role "reader"
