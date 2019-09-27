grammar dbms;

// Batch 0 Tokenizer
OPERATOR: '==' | '!=' | '<' | '>' | '<=' | '>=' ;
ALPHA: [a-zA-Z] ; // Any one character
DIGIT : [0-9] ; // Any single-digit number

integer: DIGIT+;
identifier: ALPHA ( ALPHA | DIGIT )*;
string_literal: '"' (ALPHA | DIGIT)+ '"' ;

// Batch 1
literal: (string_literal | integer) ;
relation_name: identifier ;
attribute_name : identifier;
operand : (attribute_name | literal) ;
type : ('VARCHAR' (integer) | INTEGER) ; // I don't know if this works perfectly

