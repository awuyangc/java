jQuery-Mobile-DateBox
=====================

DateBox is a jQuery-mobile based date and time picker.

This folder contains the pre-built scripts for DateBox, and the build script (make.pl)

**NOTE:** Up-to-date versions are always available to link to,
at http://dev.jtsage.com/cdb/datebox/latest/

Varients built
--------------
 * Modules: (.mode)
    * datebox/timebox
	* calbox
	* flipbox/timeflipbox
	* durationbox
	* durationflipbox
	* custombox
	* customflip
	* slidebox
 * Core Module
 * Core Module paired w/ each of the base modules (.comp)
 * Minimized Versions of Everything
 
Usage - Standard Build
----------------------

Usage: build.py [options]

    Options:
      -h, --help           show this help message and exit
      -b VER, --build=VER  Set version to build
      -v, --verbose        Verbose mode
      -q, --quiet          Quiet mode

Usage - Custom Build
--------------------

usage: custombuild.py [-h] [-v] [-q] [-f FILENAME] [--version] mods [mods ...]

Make a custom datebox build

    positional arguments:
      mods

    valid mods:
      datebox / timebox
      flipbox / timeflipbox
      calbox
      durationbox
      durationflipbox
      slidbox
      custombox
      customflipbox


    optional arguments:
      -h, --help            show this help message and exit
      -v, --verbose         Verbose mode
      -q, --quiet           Verbose mode
      -f FILENAME, --file FILENAME
                            File to output (jqm-datebox)
      --version             show program's version number and exit

