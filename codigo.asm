sseg SEGMENT STACK ;início seg. pilha
byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
byte 4000h DUP(?) ;temporários
mov ax, 0 ; const 0
mov DS:[0], al
sword ?  ; x ? inteiro
mov al, DS:[0]
mov DS:[16384], ax
sword ?  ; z ? inteiro
mov ax, 1 ; const 1
mov DS:[1], al
mov ax, 2 ; const 2
mov DS:[2], al
mov ax, 3 ; const 3
mov DS:[3], al
byte ?   ;y ? logico
byte  "'mateus'$"    ;NOME='mateus'  String
;NOME
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
ASSUME CS:cseg, DS:dseg
strt:
mov ax, dseg
mov ds, ax
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
