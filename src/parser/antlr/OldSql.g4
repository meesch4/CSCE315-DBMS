// This isC the parent SQL parser, which will probably include other grammar subsets
grammar OldSql;
// options { tokenVocab=OldSqlTokens; }

import OldSqlTokens;

main: (select | create | insert | rename | variable_assign)';';

create: CREATE ' TABLE ' tableName '(' columnCreate ')';

select: 'SELECT' WS;

insert: INSERT WS tableName;

rename: RENAME tableName tableName;

columnCreate: variable '(' TYPE ')';

type: INTEGER | FLOAT | VARCHAR | CHAR | DATE | TIME;
tableName: variable;
variable: VARIABLE SPACES?;
variable_assign: variable '<-' WS statement; // variable assigning
statement: select; // What else can it be?