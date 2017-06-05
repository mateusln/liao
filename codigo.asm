sseg SEGMENT STACK ;início seg. pilha
byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
byte 4000h DUP(?) ;temporários
sword ?  ; y ? inteiro
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
ASSUME CS:cseg, DS:dseg
strt:
mov ax, dseg
mov ds, ax
mov ax, 23 ; const 23
mov DS:[0], al
mov al, DS:[0]
mov ah, 0
mov DS:[16384], ax
mov ax, DS:[16384]
mov di, 1 ;end. string temp.
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
mov dx, 1
mov ah, 09h
int 21h
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
