# Introduction #

There are many domains where documentation management has become a necessary, almost vital tool in every day life.

RPGM uses a specific documentation indexing feature called the "Library" plugin.

This plugin aims at federating various sources of information and documentation and provide quick selection services for other features of enclosing software.

This plugin aims to be :
  * Standalone, we want to make this system usable as an eclipse plugin in the future, thus helping programmers index, reference and share docs
  * Lightweight, no full text search, no document tagging, no heavy storage
  * Flexible, usable by both gamers and programmrs alike, it might even evolve oneday to ba a one stop place for all documentation needs
  * Interoperable, drag and drop Library elements to create your domain specific items (in RPGM this means drop 10 web pages on a Scenario element and 10 Sequences elements will be created)

# Details #

This is an internal project wich currently supports :
  * PDF documents and indexes within PDF files
  * Web documents and indexes, infinite hierarchical depth
  * Audio playlists and files
  * Image files

In the future we wish to support :
  * CHM files (many eBooks use this)
  * Office files (M$Office, OpenOffice, LibreOffice)
  * Text sources
  * RSS feeds

Storage is at the time composed of XML files. Database connectivity will come soon.