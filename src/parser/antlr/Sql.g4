// This is the parent SQL parser, which will probably include other grammar subsets
grammar Sql;
options { tokenVocab=SqlTokens; }

main: (select | create | insert | variable)';';

create: CREATE 'TABLE' ;

select: ;

insert: ;

rename: ;

variable: VARIABLE WS '<-' WS statement; // variable assigning
statement: select; // What else can it be?