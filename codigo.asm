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
byte 2   ; MAX = 2  byte mem=16394
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
mov ax, 0 ; const 0
mov DS:[3], al
mov al, DS:[3]
mov DS:[16388], al; armazena byte
R0: ;rotInicio
mov ax, DS:[16388]
mov bx, DS:[16394]
cmp ax, bx
jl R2
mov AL, 0
jmp R3
R2:
mov AL, 0ffh
R3:
mov DS:[4], AL
mov al, DS:[4]
cmp al, 0
je R1
dseg SEGMENT PUBLIC
byte "'ola '$"
dseg ENDS
mov dx, 16395
mov ah, 09h
int 21h
add ax, bx
mov ax, 1 ; const 1
mov DS:[4], al
mov ax, DS:[16388]
mov bh, 0
mov bx, DS:[4]
add ax, bx
mov DS:[5], ax
mov al, DS:[5]
mov DS:[16388], ax;
jmp R0
R1: ;rotFim
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
