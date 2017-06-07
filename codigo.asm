sseg SEGMENT STACK ;início seg. pilha
byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
byte 4000h DUP(?) ;temporários
sword ?  ; n ? inteiro
byte  256 DUP (?)    ;nome? String
byte 1   ; x = 1  byte mem=16642
byte 1   ; x = 1  byte mem=16643
byte 1   ; a = 1  byte mem=16644
byte 1   ; a = 1  byte mem=16645
byte ?   ;naoTerminou ? logico
byte 10   ; MAXITER = 10  byte mem=16647
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
ASSUME CS:cseg, DS:dseg
strt:
mov ax, dseg
mov ds, ax
mov dx, 0
mov ah, 09h
int 21h
mov al, 0ffh ; const true
mov DS:[0], al
mov al, DS:[0]
mov DS:[16646], al; armazena byte
mov ax, 0 ; const 0
mov DS:[1], al
mov al, DS:[1]
mov DS:[16384], al; armazena byte
mov al, DS:[1]
mov DS:[16386], ax;
add ax, bx
mov ax, 1 ; const 1
mov DS:[2], al
mov ax, DS:[16384]
mov bh, 0
mov bx, DS:[2]
add ax, bx
mov DS:[3], ax
mov ax, 1 ; const 1
mov DS:[3], al
add ax, bx
mov ax, DS:[3]
mov bh, 0
mov bx, DS:[16643]
add ax, bx
mov DS:[4], ax
mov dx, 16643
mov ah, 09h
int 21h
add ax, bx
mov ax, 1 ; const 1
mov DS:[4], al
mov ax, DS:[16384]
mov bh, 0
mov bx, DS:[4]
add ax, bx
mov DS:[5], ax
mov al, DS:[5]
mov DS:[16384], ax;
mov al, DS:[16384]
mov DS:[16646], al; armazena byte
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
