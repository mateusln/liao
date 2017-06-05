sseg SEGMENT STACK ;início seg. pilha
byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
byte 4000h DUP(?) ;temporários
sword ?  ; m ? inteiro
sword ?  ; n ? inteiro
mov ax, 23 ; const 23
mov DS:[0], al
byte ?   ; y ? byte mem=16388
mov al, DS:[0]
mov DS:[16388], ax
byte  256 DUP (?)    ;nome? String
mov al, DS:[0]
mov DS:[16389], ax
mov ax, 1 ; const 1
mov DS:[1], al
mov ax, 300 ; const 300
mov DS:[2], al
byte ?   ;naoTerminou ? logico
mov al, DS:[2]
mov DS:[16645], ax
mov ax, 0 ; const 0
mov DS:[4], al
sword ?  ; x ? inteiro
mov al, DS:[4]
mov DS:[16646], ax
mov ax, 1 ; const 1
mov DS:[5], al
;MAXITER
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
ASSUME CS:cseg, DS:dseg
strt:
mov ax, dseg
mov ds, ax
mov ax, 0 ; const 0
mov DS:[6], al
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
