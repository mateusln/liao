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
mov ax, DS:[16388] ; endereco da variavel que vai ser impressa
mov di, 4 ;end. string temp.
mov cx, 0 ;contador
cmp ax,0 ;verifica sinal
jge R0 ;salta se numero positivo
mov bl, 2Dh ;senao, escreve sinal 
mov ds:[di], bl
add di, 1 ;incrementa indice
neg ax ;toma modulo do numero
R0:
mov bx, 10 ;divisor
R1:
add cx, 1 ;incrementa contador
mov dx, 0 ;estende 32bits p/ div.
idiv bx ;divide DXAX por BX
push dx ;empilha valor do resto
cmp ax, 0 ;verifica se quoc.  0
jne R1 ;se nao  0, continua
R2:
pop dx ;desempilha valor
add dx, 30h ;transforma em caractere
mov ds:[di],dl ;escreve caractere
add di, 1 ;incrementa base
add cx, -1 ;decrementa contador
cmp cx, 0 ;verifica pilha vazia
jne R2 ;se nao pilha vazia, loop
mov dl, 024h ;fim de string
mov ds:[di], dl ;grava '$'
mov dx, 4
mov ah, 09h
int 21h
mov ah, 02h
mov dl, 0Dh
int 21h
mov DL, 0Ah
int 21h
R3: ;rotInicio
mov ax, DS:[16388]
mov bx, DS:[16394]
cmp ax, bx
jl R5
mov AL, 0
jmp R6
R5:
mov AL, 0ffh
R6:
mov DS:[4], AL
mov al, DS:[4]
cmp al, 0
je R4
dseg SEGMENT PUBLIC
byte "'ola '$"
dseg ENDS
mov dx, 16395
mov ah, 09h
int 21h
mov ah, 02h
mov dl, 0Dh
int 21h
mov DL, 0Ah
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
jmp R3
R4: ;rotFim
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
