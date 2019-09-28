grammar dbms;
// Batch 0 Tokenizer
OPERATOR: '==' | '!=' | '<' | '>' | '<=' | '>=' ;
ALPHA: [a-zA-Z_] ;
DIGIT : [0-9] ;
integer: DIGIT+;
identifier: ALPHA (ALPHA | DIGIT)+ ;
string_literal: '"' (ALPHA | DIGIT)+ '"' ;
// Batch 1
literal: (string_literal | integer) ;
relation_name: identifier ;
attribute_name : identifier ;
operand : (attribute_name | literal) ;
type : (('VARCHAR' '('integer')') | 'INTEGER') ; // IDK if this works as intended
attribute_list : attribute_name (',' attribute_name)* ;
typed_attribute_list : attribute_name type (',' attribute_name type)* ;
open_cmd : 'OPEN' relation_name ;
close_cmd : 'CLOSE' relation_name ;
write_cmd : 'WRITE' relation_name ;
exit_cmd : 'EXIT' ;
// Batch 2
// Batch 2 rules throws left recursive error needs to be fixed
conjunction : comparison ('&&' comparison)+ ; 
condition : conjunction ('||' conjunction)+ ;
comparison : operand OPERATOR operand | (condition)+ ;




// Needs whitespace rule from Batch 0 at the end
// Add whitespace rule here
