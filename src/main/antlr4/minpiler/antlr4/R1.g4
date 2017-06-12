grammar R1;

r1
      : 'program' exp 
      ;

exp
      : integer   
      | read_exp  
      | neg_exp   
      | add_exp
      | variable
      | let_exp   
      ;

integer   
      : INT
      ;

read_exp
      : '(' 'read' ')'
      ;

neg_exp
      : '(' '-' exp ')'
      ;

add_exp
      : '(' '+' exp exp ')'
      ;

variable
      : VAR
      ;
      
let_exp
      : '(' 'let' '(' '[' variable exp ']' ')' exp ')'
      ;

INT
      : ('0' .. '9')+ 
      ;

VAR
      : ('a' .. 'z' | 'A' .. 'Z')+
      ;

WS  
      :   
      [ \t\r\n ]+ -> skip 
      ; 
