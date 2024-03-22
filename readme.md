## Danish Language Learning Application

### Description

The project allows users to create their own sets of words, including their forms if required, and provides a way to
test their knowledge of them.

### Screenshots

<img alt="Simple Word Learning" src="https://github.com/fedor-povarov/danish-language-learning/blob/assets/assets/images/simpleWordLearning.png?raw=true" width="768" height="393">
<img alt="Nouns" src="https://github.com/fedor-povarov/danish-language-learning/blob/assets/assets/images/nouns.png?raw=true" width="768" height="393">

### Requirements

To run the project you need:

1. Java 11
2. Docker
3. Lombok Plugin for Intellij Idea

### Launching

To start the project:

1. Start database and PGAdmin
    1. Go to docker-compose directory and run [restart.sh](docker-compose/restart.sh)
    2. This will start:
        1. PostgreSQL DB - port 5432 - necessary for backend of the application
        2. PGAdmin - port 5434 - provides convenient way to configure database
    3. Wait for message in the console: db_1 | database system is ready to accept connections
2. Check DB availability
    1. Go to http://localhost:5434
    2. Credentials
        1. Login: test@test.test
        2. Password: admin
    3. On left hand side, right mouse button at Servers -> Create -> Server
    4. General tab
        1. name: localhost
    5. Connection tab
        1. Host name: db
        2. Port: 5432
        3. Maintenance database: postgres
        4. Username: postgres
        5. Password: postgres
        6. Save password?: toggle enabled
    6. Hit save
    7. New connection 'localhost' should appear
    8. Expand localhost -> Databases -> danish_language_learning -> schemas -> public -> tables
    9. At the first launch there will be no tables, but after application launch new tables with sample data should
       appear
3. Start application from Intellij Idea
    1. Open file src/main/java/org/fedor/povarov/danish/language/learning/DanishLanguageLearningApplication.java
    2. Click green arrow next to class name
    3. Click 'Run DanishLanguageLearningApplication.main()'
4. Open the site in browser
    1. Go to url http://localhost:8087
    2. Start using the project

### Usage

Simple word/Noun/Verb List Pages: Create, edit, view, and delete words using the word list pages. These pages provide a
user-friendly interface for managing your vocabulary.
Simple word/Noun/Verb Learning Pages: Test your knowledge of imported words using the learning pages. These pages offer
interactive exercises to help you learn and remember words effectively.

### Contributing

The preferred way to contribute would be through GitHub pull requests.

### License

This application is free and unencumbered software released into the public domain. For more information, see the
included [LICENSE.txt](LICENSE.txt).
