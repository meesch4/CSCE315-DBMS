// This is the parent SQL parser, which will probably include other grammar subsets
grammar Sql;
options { tokenVocab=SqlTokens; }

rename: (select | create | insert)';';

create: CREATE 'TABLE' ;

select: ;

insert: ;

