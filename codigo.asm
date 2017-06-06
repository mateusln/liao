sseg SEGMENT STACK ;início seg. pilha
byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
byte 4000h DUP(?) ;temporários
byte ?   ; x ? byte mem=16384
sword ?  ; z ? inteiro
byte ?   ;y ? logico
byte  "'mateus'$"    ;NOME='mateus'  String
;NOME
byte ?   ; flag ? byte mem=16644
byte 1   ; teste = 1  byte mem=16645
;teste
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
ASSUME CS:cseg, DS:dseg
strt:
mov ax, dseg
mov ds, ax
mov al, 0ffh ; const true
mov DS:[0], al
mov al, DS:[0]
mov DS:[16387], al; armazena byte
mov ax, 14 ; const 14
mov DS:[1], al
mov ax, 2 ; const 2
mov DS:[2], al
mov ax, DS:[1]
mov bx, DS:[2]
mov dx, 0
div bx ; divisao
mov DS:[3], ax
mov al, DS:[3]
mov DS:[16384], al; armazena byte
mov ax, 1 ; const 1
mov DS:[4], al
mov al, DS:[4]
mov DS:[16385], al; armazena byte
mov ax, DS:[16387]
cmp al, 0
je R0; rotulo falso
mov ax, DS:[16384] ; endereco da variavel que vai ser impressa
mov di, 5 ;end. string temp.
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
mov dx, 5
mov ah, 09h
int 21h
mov dx, 16388
mov ah, 09h
int 21h
R0:
mov ah, 4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
