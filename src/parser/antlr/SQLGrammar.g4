grammar SQLGrammar;

// Batch 0 Tokenizer
OPERATOR: '==' | '!=' | '<' | '>' | '<=' | '>=' ;
ALPHA: [a-zA-Z_] ;
DIGIT : [0-9] ;
operator: OPERATOR;
integer: DIGIT+;
identifier: ALPHA (ALPHA | DIGIT)* ;
string_literal: '"' (ALPHA | DIGIT)+ '"' ;

// Batch 1
literal: (string_literal | integer) ;
relation_name: identifier ;
attribute_name : identifier ;
operand : (attribute_name | literal) ;
type : (('VARCHAR' '('integer')') | 'INTEGER') ;
literal_list : literal ( ',' literal)* ;
attribute_list : attribute_name (',' attribute_name)* ;
typed_attribute_list : attribute_name type (',' attribute_name type)* ;
open_cmd : 'OPEN' relation_name ;
close_cmd : 'CLOSE' relation_name ;
write_cmd : 'WRITE' relation_name ;
exit_cmd : 'EXIT' ;

or: '||';
and: '&&';

// Batch 2
condition : conjunction (or conjunction)* ;
conjunction : comparison (and comparison)* ;
comparison : operand operator operand | '('condition')' ;

// Batch 3
expr : atomic_expr | selection | projection | renaming | union | difference | product | natural_join ;
atomic_expr : relation_name | '('expr')' ;
selection : 'select' '(' condition ')' atomic_expr ;
projection : 'project' '(' attribute_list ')' atomic_expr ;
renaming : 'rename' '('attribute_list')' atomic_expr ;
union : atomic_expr '+' atomic_expr ;
difference : atomic_expr '-' atomic_expr ;
product : atomic_expr '*' atomic_expr ;
natural_join : atomic_expr '&' atomic_expr ;

// Batch 4
update_set_list: attribute_name '=' literal ( ',' attribute_name '=' literal)* ;
show_cmd : 'SHOW' atomic_expr ;
create_cmd : 'CREATE TABLE' relation_name '(' typed_attribute_list ')' 'PRIMARY KEY' '('attribute_list')' ;
update_cmd : 'UPDATE' relation_name
             'SET' update_set_list
             'WHERE' condition ;
insert_cmd : 'INSERT INTO' relation_name 'VALUES FROM' '('literal_list')'
           | 'INSERT INTO' relation_name 'VALUES FROM RELATION' expr ;
delete_cmd : 'DELETE FROM' relation_name 'WHERE' condition ;
// Batch 5
// Semicolon issues fixed && query issues fixed
command : ( open_cmd | close_cmd | write_cmd | exit_cmd | show_cmd
            | create_cmd | update_cmd | insert_cmd | delete_cmd  ) ';' ;
query : relation_name '<-' expr ';' ;
program : (query | command) ;

// Batch 0 white space removal
WHITE_SPACE : [ \n\t\r]+ -> skip ;
