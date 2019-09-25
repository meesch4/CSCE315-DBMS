grammar test;
prule: (select | create | insert)';'; // Must end with a semi-colon
select: 'SELECT';

create: 'CREATE' createType '(' columnNames ')' 'PRIMARY_KEY' primaryKeys;
createType: 'TABLE';
primaryKeys: 'primaryKeys';

insert: 'INSERT INTO ' tableName ' '? 'VALUES FROM ' '(' tableValues ')';
tableName: variableName ' '; // Regex here for tableNames? Don't care what the name is as long as it has valid characters
tableValues: ' '? (variableName | variableName ',' tableValues) ' '?;

columnNames: 'regex' type | 'regex' type ',';
type: 'type';

variableName: ANY+;
ANY: .;