sseg SEGMENT STACK ;início seg. pilha
byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
byte 4000h DUP(?) ;temporários
byte 10   ; z = 10  byte mem=16384
sword 10   ; z=10   inteiro
byte 12   ; x = 12  byte mem=16387
sword 12   ; x=12   inteiro
byte ?   ;y ? logico
byte 10   ; flag = 10  byte mem=16391
sword 10   ; flag=10   inteiro
byte 1   ; teste = 1  byte mem=16394
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
ASSUME CS:cseg, DS:dseg
strt:
mov ax, dseg
mov ds, ax
mov al, 0ffh ; const true
mov DS:[0], al
mov al, 0h ; const false
mov DS:[1], al
mov ax, DS:[0]
mov bh, 0
mov bx, DS:[1]
or ax, bx
mov DS:[2], ax
mov al, DS:[2]
mov DS:[16390], al; armazena byte
mov ax, 1 ; const 1
mov DS:[2], al
mov al, DS:[2]
mov DS:[16385], al; armazena byte
mov ax, 14 ; const 14
mov DS:[3], al
mov ax, DS:[3]
mov bh, 0
mov bx, DS:[16385]
sub ax, bx
mov DS:[4], ax
mov al, DS:[4]
mov DS:[16388], ax;
mov ax, 1 ; const 1
mov DS:[4], al
mov ax, 1 ; const 1
mov DS:[5], al
mov ax, DS:[4]
mov bx, DS:[5]
cmp ax, bx
jge R2
mov AL, 0
jmp R3
R2:
mov AL, 0ffh
R3:
mov DS:[6], AL
mov ax, DS:[6]
cmp al, 0
je R0; rotulo falso
dseg SEGMENT PUBLIC
byte "'suamae'$"
dseg ENDS
mov dx, 16395
mov ah, 09h
int 21h
R0:
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
