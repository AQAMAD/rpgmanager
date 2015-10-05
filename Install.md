# Step 1 : Download #

The first step to get RPGM up and running is to [download](http://code.google.com/p/rpgmanager/downloads/list) a package.

Choose the appropriate package from the list, the latest stable version is always named RPGM4xxxx.zip where xxx is the operating system you are currently using.

# Step 2 : Check for prerequisites #

Great, you just downloaded the file and want to try it. But before that you need to have Java up and running on your system. The steps to install and setup java are beyond the scope of this page, but you will find more information there : [www.java.com Official Java Site]

If you're asking what flavor you need, keep in mind you need Java 6, JRE or JDK is fine, or any other Java 6 compatible virtual machine.

RPGM is built on top of the Eclipse RCP stack, which has been reported to have trouble with a certain Java 6 VM from Oracle. This bug has been patched, so just update to the latest Java VM and you're good to go.

# Step 3 : Unpack the file #

You downloaded RPGM as a zip file, to run it, you need to extract the contents of this file into a directory. Most probably you only have to right-click on the file you downloaded, and select "Extract here" or "Extract All" from the popup menu. You might get a warning about overwriting some files, just click "overwrite" and you should be good.

Once the extraction is complete you'll get a new directory named "com.delegreg.rpgm" which contains :
  * a RPGM.exe file (on windows, RPGM on linux, or a RPGM.App folder on Mac)
  * a RPGM.ini file (again on windows and linux, it's in the RPGM.App folder on Mac)
  * a plugins folder
  * a features folder
  * a configuration folder

Congratulations, if you've come this far, you can already launch RPGM by using the first file (usually by double-clicking it)

Some considerations however :

Neat freaks might want to copy or move the directory to the windows "Program Files". You can, but be aware that this specific folder is read-only under Vista or Seven, so you might want to run RPGM.exe with Administrator privileges.

If you use a shortcut of some kind to run RPGM, make sure it runs in the appropriate directory (RPGM doesn't need its INI configuration file, but you'll run with very little memory available, so this might cause problems)

# Step 4 : Use plugins #

A D20 plugin is actually under construction but already functional. To use it, download and copy the appropriate "jar" file to your "plugins" folder and restart RPGM.

An update site is under construction to host RPGM update ans plugins (extensions) but it will not be available before a few weeks.