/* Exemplo de Programa na linguagem L */

integer n=1 + 2;
string nome=-'string';
boolean naoTerminou= 1 != 300;
integer x=0;
const MAXITER=1;
/* Programa Principal */
main
  write('Digite seu nome: ');
  readln(nome);
  naoTerminou=true;
  if (naoTerminou)  then begin
    n=0;
    writeln('true');
    readln(nome);
    naoTerminou=n<MAXITER;
  end
end
