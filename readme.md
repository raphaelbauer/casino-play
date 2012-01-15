![Casino](https://github.com/reyez/casino-play/blob/master/casino-logo.png?raw=true)

# Casino! - user management for Play! framework

## Description

Play is a great framework but currently lacks a simple user sign-up and password recovery process.

This project provides a simple method to integrate sign-up and password recovery to your project.
The cool thing is that it is based on play's security concept. So we don't re-invent the wheel but simply add some stuff.


## Features
* Signup / Registration of new users
* Sending of Signup email for email verification
* ReCaptcha protection against bots
* Password recovery
* Login via different url (eg. login / registration via https, rest of app handleded via http)
* Simple management of arbitrary roles
* Support for Siena ORM (planned support for other ORMs).


## Next possible todos
* Time based invalidation for confirmation codes in User model
* maybe different User.java (docs, empty fields not intuitive?)
* OAuth integration => facebook login / google login 


## Friends on the road
  * https://github.com/steve918/play-registration (got a lot ideas what to do from there thanks!)
  * http://www.grails.org/plugin/gsec
  * http://www.playframework.org/modules/recaptcha-1.2/home
  * https://github.com/mandubian/play-siena
  * http://playframwork.org
  * http://sienaproject.com/

