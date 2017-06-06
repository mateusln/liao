sseg SEGMENT STACK ;início seg. pilha
byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
byte 4000h DUP(?) ;temporários
byte ?   ;y ? logico
byte ?   ; x ? byte mem=16385
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
ASSUME CS:cseg, DS:dseg
strt:
mov ax, dseg
mov ds, ax
mov al, 0h ; const false
mov DS:[0], al
mov al, DS:[0]
mov DS:[16384], al; armazena byte
mov ax, 13 ; const 13
mov DS:[1], al
mov al, DS:[1]
mov DS:[16385], al; armazena byte
mov ax, 23 ; const 23
mov DS:[2], al
mov al, DS:[2]
mov DS:[2], al; armazena byte
mov al, DS:[16384] ;
not al
mov DS:[16384], al
mov al, DS:[16384]
mov DS:[16384], al; armazena byte
mov ax, DS:[16384]
cmp al, 0
je R0; rotulo falso
mov ax, DS:[16385] ; endereco da variavel que vai ser impressa
mov di, 3 ;end. string temp.
mov cx, 0 ;contador
cmp ax,0 ;verifica sinal
jge R2 ;salta se numero positivo
mov bl, 2Dh ;senao, escreve sinal 
mov ds:[di], bl
add di, 1 ;incrementa indice
neg ax ;toma modulo do numero
R2:
mov bx, 10 ;divisor
R3:
add cx, 1 ;incrementa contador
mov dx, 0 ;estende 32bits p/ div.
idiv bx ;divide DXAX por BX
push dx ;empilha valor do resto
cmp ax, 0 ;verifica se quoc.  0
jne R3 ;se nao  0, continua
R4:
pop dx ;desempilha valor
add dx, 30h ;transforma em caractere
mov ds:[di],dl ;escreve caractere
add di, 1 ;incrementa base
add cx, -1 ;decrementa contador
cmp cx, 0 ;verifica pilha vazia
jne R4 ;se nao pilha vazia, loop
mov dl, 024h ;fim de string
mov ds:[di], dl ;grava '$'
mov dx, 3
mov ah, 09h
int 21h
R0:
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
