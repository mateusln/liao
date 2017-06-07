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
byte  "'mateus'$"    ;NOME='mateus'  String
byte 10   ; flag = 10  byte mem=16647
sword 10   ; flag=10   inteiro
byte 1   ; teste = 1  byte mem=16650
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
jle R2
mov AL, 0
jmp R3
R2:
mov AL, 0ffh
R3:
mov DS:[6], AL
mov ax, DS:[6]
cmp al, 0
je R0; rotulo falso
mov ax, DS:[16388] ; endereco da variavel que vai ser impressa
mov di, 6 ;end. string temp.
mov cx, 0 ;contador
cmp ax,0 ;verifica sinal
jge R4 ;salta se numero positivo
mov bl, 2Dh ;senao, escreve sinal 
mov ds:[di], bl
add di, 1 ;incrementa indice
neg ax ;toma modulo do numero
R4:
mov bx, 10 ;divisor
R5:
add cx, 1 ;incrementa contador
mov dx, 0 ;estende 32bits p/ div.
idiv bx ;divide DXAX por BX
push dx ;empilha valor do resto
cmp ax, 0 ;verifica se quoc.  0
jne R5 ;se nao  0, continua
R6:
pop dx ;desempilha valor
add dx, 30h ;transforma em caractere
mov ds:[di],dl ;escreve caractere
add di, 1 ;incrementa base
add cx, -1 ;decrementa contador
cmp cx, 0 ;verifica pilha vazia
jne R6 ;se nao pilha vazia, loop
mov dl, 024h ;fim de string
mov ds:[di], dl ;grava '$'
mov dx, 6
mov ah, 09h
int 21h
mov dx, 16391
mov ah, 09h
int 21h
R0:
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
