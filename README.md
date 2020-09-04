

<p align="center"><a href="https://boneos.org" target="_blank"><img width="200"src="https://i.imgur.com/TNO0fqq.png"></a></p>
<h1 align="center" id="header" style="color:#21d4fd;"><a href="#">Servo</a></h1>

Android project which allows users to pay for others services.

Services range from anything to coding(freelancing) to producing beats
to paying ur neighbor to maw your lawn :)


# Releases
<table>
  <tr>
    <th>Release Version</th>
    <th>Release Date</th>
    <th>Android APK's</th>
  </tr>

  <tr>
   <td>V 0.1</td>
   <td>09/04/2020</td>
   <td>
     <a href='https://play.google.com/store/apps/details?id=com.servo.auth&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'>
      <img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' width="200"/>
     </a>
   </td>
  </tr>
  </tr>
</table>

# Installation

In order to run this application in your enviorement go ahead and install android studio from <b><a href="https://developer.android.com">here</a></b> first. Then after you install just import the
project and all the gradle dependancies should automatically sync!

## Dependancies

The external dependancies that are used to run this project currently include

    - Android X libraries 
        - Default libraries are needed
        - Navigation UI for navigation
        - ViewPager 2 for navigation via swipe
        
    - Airbnb
        - Lottie Animation used for appealing animations
        
    - JTDS, a JDBC Driver neccecsary for secure connection to a SQL Server
    
    - Picasso for image loading
    
    - Google Play Services used for google authentication
    
    - Facebook auth used for facebook authentication

# Directory Structures
- ***gradle*** : Gradle settings
- ***build.gradle*** : Gradle build at project level
- ***app*** : Main app source code
   - ***src/main***
     - ***java/com/servo***: All main java backend code located here
       - ***adapter***: Custom Adapters
       - ***auth***: Authentication & Splash Activities
       - ***database***: Custom Database Base Classes
       - ***dialog***: Custom dialogs such as success/error
       - ***home***: Homepage Activities
       - ***utils***: Utilities such as constant and string manupilation
       - ***view***: View holders located here
     - ***res***: All XML Layouts, Colors, Drawables,etc...
       - ***anim***: Animation files
       - ***color***: All selected/non-selected colors
       - ***drawable***: images and vectors
       - ***font***: fonts uploaded
       - ***layout***: main layout files
       - ***menu***: menu's for transition
       - ***navigation***: Navigation system
       - ***raw***: Raw files for Lottie
       - ***values***: Constant values used throughout
     - ***AndroidManifest*** : Manifest file for managing activities
     - ***link***: Where the linker is located.
  - ***build.gradle*** : Gradle build at app module level 


# Discussions

If you have any concerns in this app make sure to issue a request in the issues tab. If you have ideas of improvments and would like to help, feel free to send a pull request!

# FAQ

<b>How much progress have you made?</b> I have essentially finished the registration system, home page feeds, searching for other users and services as well as adding services, and checking out your own profile!

<b> Is this application available in the google play store ?</b> Yes it is available! Look at the releases above in the README and select the version you would like!

# LICENCE

MIT License

Copyright (c) 2020 Servo-Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
