//Ketan Kumar
//This is the lexer component of a compiler
//It was made for Simple C (a subset of C)


#include <iostream>
#include <ctype.h>
#include <stdio.h>
#include <string.h>

using namespace std;


string keywords[32] = {"auto", "break", "case", "char", "const", "continue", "default", "do", "double", "else", "enum", "extern", "float", "for", "goto", "if", "int", "long", "register", "return", "short", "signed", "sizeof", "static", "struct", "switch", "typedef", "union", "unsigned", "void", "volatile", "while"};


//returns whether a token is a keyword or not
int isKeyWord(string token)
{
	for(int i = 0; i < 32; i++)
	{   
		if(strcmp(keywords[i].data(), token.data()) == 0)
		{   
			return 1;
		}   
	}   
	return 0;
}



void lexer()
{
	char currentChar;
	char peekChar;
	char nextChar;

	//reads the input program and splits it into tokens
	//looks for spaces, newlines, or tabs as characters that separate tokens
	do
	{
		currentChar = cin.get();
		if(currentChar == ' ' || currentChar == '\n' || currentChar == '\t')
		{
			continue;
		}
		//the case for keywords or identifiers
		else if(isalpha(currentChar) || currentChar == '_')
		{
			string token;
			
			while(!cin.eof() && (isalpha(currentChar) || isdigit(currentChar) || currentChar == '_'))
			{
				token.push_back(currentChar);
				currentChar = cin.get();
			}

			cin.putback(currentChar);

			if(isKeyWord(token) == 1)
			{
				cout << "keyword:" << token.data() << "\n";
			}
			else
			{
				cout << "identifier:" << token.data() << "\n";
			}
		}
		//the case for comments
		else if(currentChar == '/')
		{
			char nextChar = cin.get();
			if(nextChar != '*')
			{
				cin.putback(nextChar);
				cout << "operator:" << currentChar << "\n";
				continue;
			}
			else{
				while(nextChar != '/' || currentChar != '*')
				{
					currentChar = nextChar;
					nextChar = cin.get();
				}
			}
		}
		//checks to see if the token is an digit
		else if(isdigit(currentChar))
		{
			string number;
			while(!cin.eof() && isdigit(currentChar))
			{
				number.push_back(currentChar);
				currentChar = cin.get();
			}
			cin.putback(currentChar);

			cout << "number:" << number.data() << "\n";
		}
		//sees if current token is a character
		else if(currentChar == '\'')
		{
			string characterString;
			characterString.push_back(currentChar);
			currentChar = cin.get();

			while(currentChar != '\'')
			{
				characterString.push_back(currentChar);

				if(currentChar == '\\')
				{
					currentChar = cin.get();
					characterString.push_back(currentChar);	
				}

				currentChar = cin.get();
			}

			characterString.push_back(currentChar);
			cout << "character:" << characterString.data() << "\n";
		}
		//sees if current token is a string.
		else if(currentChar == '\"')
		{
			string stringData;
			stringData.push_back(currentChar);
			currentChar = cin.get();

			while(currentChar != '\"')
			{
				stringData.push_back(currentChar);

				if(currentChar == '\\')
				{
					currentChar = cin.get();
					stringData.push_back(currentChar);	
				}

				currentChar = cin.get();
			}

			stringData.push_back(currentChar);
			cout << "string:" << stringData.data() << "\n";
		}
		else if(currentChar == '=' || currentChar == '&' || currentChar == '+')
		{
			peekChar = cin.peek();

			if(peekChar == currentChar)
			{
				cout << "operator:" << currentChar << peekChar << "\n";
				currentChar = cin.get();
			}
			else{
				cout << "operator:" << currentChar << "\n";
			}
		}
		else if(currentChar == '>' || currentChar == '<' || currentChar == '!')
		{
			peekChar = cin.peek();

			if(peekChar == '=')
			{
				cout << "operator:" << currentChar << peekChar << "\n";
				currentChar = cin.get();
			}
			else{
				cout << "operator:" << currentChar << "\n";
			}
		}
		else if(currentChar == '-')
		{
			peekChar = cin.peek();

			if(peekChar == '-' || peekChar == '>')
			{
				cout << "operator:" << currentChar << peekChar << "\n";
				currentChar = cin.get();
			}
			else{
				cout << "operator:" << currentChar << "\n";
			}
		}
		else if(currentChar == '|')
		{
			peekChar = cin.peek();

			if(peekChar == '|')
			{
				cout << "operator:" << currentChar << peekChar << "\n";
				currentChar = cin.get();
			}
		}
		else if(currentChar == '*' || currentChar == '%' || currentChar == '.' || currentChar == '(' ||
		currentChar == ')' || currentChar == '[' || currentChar == ']' || currentChar == '{' || 
		currentChar == '}' || currentChar == ';' || currentChar == ':' || currentChar == ',')
		{
			cout << "operator:" << currentChar << "\n";
		}
	}
	while(!cin.eof());
}


int main()
{
	lexer();
	
	return 0;
}
