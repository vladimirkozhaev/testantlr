grammar AntlrTest;

jobs:job (ENTER ENTER+ job)* ENTER*;
job:SPACE? tagName=ID SPACE jobName=ID ENTER desc=description;

description:SPACE? ID (SPACE ID)* (ENTER SPACE? ID (SPACE ID)*)* ;

SPACE:(' '|'\t')+;
ENTER:'\n'|'\r';
ID
:
	[a-zA-Z0-9]+
;
