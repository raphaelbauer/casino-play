![Casino](https://github.com/reyez/casino-play/blob/master/casino-logo.png?raw=true)

# Casino! - user management for Play! framework

## Description

This project provides a simple method to integrate user registration and password recovery to your project.
The cool thing is that it is based on play's security concept. So we don't re-invent the wheel 
but simply add some stuff.


## Features
* Signup / Registration of new users
* Sending of signup email for email verification
* ReCaptcha protection against bots
* Password recovery
* Login via different url (eg. login / registration via https, rest of app handled via http)
* Simple management of arbitrary roles
* Support for Siena ORM (planned support for other ORMs).
* AfterUserCreationHook => do something when a user has been created in your db.

# Getting started
* Add - play -> casino-xx to your dependencies.yml
* Add the following properties to your application conf:
    * ugot.recaptcha.privateKey=YOUR RECAPTCHA KEY
    * ugot.recaptcha.publicKey=YOUR RECAPTCHA KEY
    * more on recaptcha keys: http://www.playframework.org/modules/recaptcha-1.2/home
    * casino.emailFrom=YOUR_EMAIL@test.com 
    * (optional) casino.regularUrl=https://www.example.com (to fix regular url, eg your http url) 
    * (optional) casino.secureUrl=https://www.example.com (to fix secure url, eg your https url)
* check out test config at: https://github.com/reyez/casino-play/blob/master/casino-test/conf/application.conf

# More getting started
* General lifecycle: https://github.com/reyez/casino-play/blob/master/casino-test/test/RegistrationLifecycle.test.html
* General configuration: https://github.com/reyez/casino-play/blob/master/casino-test/conf

# Examples
* yourapp.com/registration => signup new users including email verification
* yourapp.com/registration/lostpassword => recover password by sending mail to user

# Default routes provided by security module (distributed by play itself):
* yourapp.com/login
* yourapp.com/logout

# Implementation details
* Uses siena for persistence of the user object 
* Uses bcrypt to hash password (should be safe in the next years)
* Uses re-captcha to block bots from using your stuff
* complete selenium usecase is at casino-test/test/!RegistrationLifecycle.html

# Develop 
## Next possible todos
* Time based invalidation for confirmation codes in User model
* maybe different User.java (docs, empty fields not intuitive?)
* OAuth integration => facebook login / google login 


## Setup and develop
* clone sources from github
* generate a link to casino from your play/modules directory:
    * ln -s mycheckoutdirectory/casino-play/casino casino-0.x (x is the version you want to reference)
* run "play deps --sync" and "play ec" in both casino and casino-test
* run "play auto-test" in casino-test - the result should be "All tests passed"
* import projects into eclipse if you wish
... Happy coding :)

# Test
* Test project is under casino-test
* Run tests via "play test" or "play auto-test"

# Release
* play build-module
* Use /dist/casino-0.X.zip for distribution :)

# Friends on the road
* https://github.com/steve918/play-registration (got a lot ideas what to do from there thanks!)
* http://www.grails.org/plugin/gsec
* http://www.playframework.org/modules/recaptcha-1.2/home
* https://github.com/mandubian/play-siena
* http://playframwork.org
* http://sienaproject.com/

[Supported by FinalFrontierLabs][http://finalfrontierlabs.com]
