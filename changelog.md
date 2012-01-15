Changelog
==============

1.0 (latest)
-----------
* name change to Casino
* move to github
* change of file names to "Casino" prefix (CasioSecurity, CasinoSecure...)


0.4
-----------
* cleaner integration with Play secure => still doublication of Secure.java and Check.java. Not nice.
* support for authorizing (thus setting play session cookies) user across domain boundaries
** routes login/auth_with_token?token=XYZ logout/auth_with_token?token=XYZ
** usecase was: https://example.appspot.com signs http://appspot.com (SessionTransfer controller)
* Support for Roles in User
** Methods hasRole, addRole
** @Check("role:admin") (or any role you use) supported on controller level

0.3:
Description
-----------
* support for fixing url for login/login/registration
** example: make sure login runs over https://example.com and NOT over http://example.com
** done via parameter
* moved stuff into subdirectory playsienauser