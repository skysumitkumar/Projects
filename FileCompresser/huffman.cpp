#include "huffman.hpp"

void huffman::createArr()
{
    for(int i=0;i<128;i++)
    {
        arr.push_back(new Node());
        arr[i]->data=i;
        arr[i]->freq=0;
    }
}

void huffman::createMinHeap()
{
    char id;
    //ios::in: Open for input operations (reading).
    inFile.open(inFileName,ios::in);
    //Incrementing frequency of characters that appear in the input file
    //The inFile.eof() function in C++ is used to check whether the end-of-file (EOF) has been reached during file reading operations.
    //This function reads the next character from the file and stores it in id.If the read fails (e.g., due to reaching EOF), id will not be updated, and the loop condition will eventually fail in the next iteration.
    while(inFile.get(id))
    {
        //This line increments the frequency count of the character stored in id.
        arr[id]->freq++;// This array uses the ASCII value of the character id as an index.
    }
    //close the file
    inFile.close();
    //Pushing the Nodes which appear in the file into the priority queue (Min Heap)
    for(int i=0;i<128;i++)
    {
        if(arr[i]->freq>0)
        {
            minHeap.push(arr[i]);
        }
    }
}

void huffman::createTree()
{
    //creating huffman tree with the min heap created earlier
    Node* left;
    Node* right;
    priority_queue<Node*,vector<Node* >,Compare>tempPQ(minHeap);
    while(tempPQ.size()!=1)
    {
        left=tempPQ.top();
        tempPQ.pop();
        right=tempPQ.top();
        tempPQ.pop();
        root=new Node();
        root->freq=left->freq+right->freq;
        root->left=left;
        root->right=right;
        tempPQ.push(root);
    }
}

void huffman::traverse(Node* r,string str)
{
    if(r->left==NULL&&r->right==NULL)
    {
        r->code=str;
        return;
    }
    traverse(r->left,str+'0');
    traverse(r->right,str+'1');
}
void huffman::createCodes()
{
    //Traversing the huffman tree and assigning specific codes to each character
    traverse(root,"");
}

int huffman::binToDec(string inStr)
{
    int res=0;
    for(auto c:inStr)
    {
        res=res*2+c-'0';
    }
    return res;
}
string huffman::decToBin(int inNum) 
{
    string temp="",res="";
    while(inNum>0) 
    {
        temp+=(inNum%2+'0');// Append remainder (0 or 1) as a character to `temp`
        inNum/=2;// Divide `inNum` by 2 to process the next bit
    }
    //8-temp.length(): Calculates the number of leading '0's needed to make the binary representation 8 bits long.
    res.append(8-temp.length(),'0');// Pad `res` with leading '0's to ensure 8 bits
    for(int i=temp.length()-1;i>=0;i--) 
    {
        res+=temp[i];// Append the bits from `temp` in reverse order to `res`
    }
    return res;
}
//It includes both the metadata (Huffman tree) and the actual encoded data
void huffman::saveEncodedFile()
{
    //Saving encoded(.huf) file
    //open the input file for reading
    inFile.open(inFileName,ios::in);
    //This flag indicates that the file is opened for output (writing).This flag indicates that the file is opened in binary mode, meaning that data will be written in binary format rather than text format.
    outFile.open(outFileName,ios::out|ios::binary);
    //A string to store the data that will be written to the output file.
    string in="";
    //A temporary string used for building binary codes.
    string s="";
    //char to read the input file
    char id;
    //saving the meta data (huffman tree)
    //Adds the size of the minHeap (number of unique characters) to the in string.
    in+=(char)minHeap.size();
    //temp priority queue to traverse the minHeap
    priority_queue<Node*,vector<Node*>,Compare>tempPQ(minHeap);
    while(!tempPQ.empty())
    {
        Node* curr=tempPQ.top();
        //add the char in the in string
        in+=curr->data;
        //Saving 16 decimal values representing code of curr->data
        //curr->code like '0010' etc we take its length and sub from 127 so we have some length then we assign 0 to string of that length
        //Initializes s with enough '0's so that the total length of s (after appending '1' and the Huffman code) is 128.
        s.assign(127-curr->code.length(),'0');
        //Appends a '1' to the string to serve as a delimiter.
        s+='1';
        //append binary code(Huffman code) to the string
        s+=curr->code;
        //Converts the first 8 bits of s to a decimal value using binToDec and adds it to in.
       //Repeats for the next 8 bits until all bits of s are processed.
        in+=(char)binToDec(s.substr(0,8));
        for(int i=0;i<15;i++)
        {
            s=s.substr(8);
            in+=(char)binToDec(s.substr(0,8));
        }
        tempPQ.pop();
    }
    s.clear();
    //Saving codes of every character appearing in the input file
    inFile.get(id);
    while(!inFile.eof())
    {
        //Appends the Huffman code for the character to s.
        s+=arr[id]->code;
        //Saving decimal values of every 8 bit binary code;
        while(s.length()>8)
        {
            in+=(char)binToDec(s.substr(0,8));
            s=s.substr(8);
        }
        inFile.get(id);
    }
    //Calculates the number of '0's needed to make the final segment 8 bits long.
    int count=8-s.length();
    if(s.length()<8)
    {
        s.append(count,'0');
    }
    in+=(char)binToDec(s);
    //append count of appended 0's
    in+=(char)count;
    //write the in string to the output file
    outFile.write(in.c_str(),in.size());
    inFile.close();
    outFile.close();
}

void huffman::saveDecodedFile()
{
    inFile.open(inFileName,ios::in|ios::binary);
    //Opens the output file in text mode for writing.
    outFile.open(outFileName,ios::out);
    //to store the size of huffman tree
    unsigned char size;
    //reads one byte from the input file into the size variable.
    inFile.read(reinterpret_cast<char*>(&size),1);
    //Reading count at the end of the file which is number of bits appended to make final value 8-bit
    //Moves the file pointer to the last byte of the file.
    inFile.seekg(-1,ios::end);
    char count0;// to store the number of padding bits added to the last byte.
    //read the last byte in count0
    inFile.read(&count0,1);
    //Ignoring the meta data (huffman tree)(1+17*size)and reading remaining file actual data
    inFile.seekg(1+17*size,ios::beg);
    //to store encoded data
    vector<unsigned char>text;
    unsigned char textseg;
    inFile.read(reinterpret_cast<char*>(&textseg),1);
    while(!inFile.eof())
    {
        //add each read byte
        text.push_back(textseg);
        inFile.read(reinterpret_cast<char*>(&textseg),1);
    }
    Node* curr=root;
    string path;
    for(int i=0;i<text.size()-1;i++)
    {
        //converting decimal no to its equivalent 8-bit binary no
        path=decToBin(text[i]);
        if(i==text.size()-2)
        {
            //Removes the padding bits from the last byte's binary representation.
            path=path.substr(0,8-count0);
        }
        //Traversing huffman tree and appending resultant data to the file
        for(int j=0;j<path.size();j++)
        {
            if(path[j]=='0')
            {
                curr=curr->left;
            }
            else
            {
                curr=curr->right;
            }
            if(curr->left==NULL&&curr->right==NULL)
            {
                outFile.put(curr->data);
                curr=root;
            }
        }
    }
    inFile.close();
    outFile.close();
}
void huffman::getTree() 
{
    inFile.open(inFileName,ios::in|ios::binary);
    //Reading size of MinHeap
    unsigned char size;
    inFile.read(reinterpret_cast<char*>(&size),1);
    root=new Node();
    //next size * (1 + 16) characters contain (char)data and (string)code[in decimal]
    for(int i=0;i<size;i++) 
    {
        //Declares a variable to store a character from the metadata.
        char aCode;
        unsigned char hCodeC[16];
        inFile.read(&aCode,1);
        inFile.read(reinterpret_cast<char*>(hCodeC),16);
        //converting decimal characters into their binary equivalent to obtain code
        string hCodeStr="";
        for(int i=0;i<16;i++) 
        {
            hCodeStr+=decToBin(hCodeC[i]);//Converts each byte from decimal to its 8-bit binary representation and appends it to hCodeStr
        }
        //Initializes an index to locate the start of the actual Huffman code.
        int j=0;
        while(hCodeStr[j]=='0') 
        {
            j++;
        }
        //Removes the leading '0's and the first '1' (delimiter) from hCodeStr, leaving only the actual Huffman code.
        hCodeStr=hCodeStr.substr(j+1);
        //Adding node with aCode data and hCodeStr string to the huffman tree
        buildTree(aCode,hCodeStr);
    }
    inFile.close();
}
void huffman::buildTree(char a_code, string& path) 
{
    Node* curr=root;
    for (int i=0;i<path.length();i++) 
    {
        if(path[i]=='0') {
            if(curr->left==NULL) 
            {
                curr->left=new Node();
            }
            curr=curr->left;
        }
        else if(path[i]=='1') 
        {
            if(curr->right==NULL) 
            {
                curr->right=new Node();
            }
            curr=curr->right;
        }
    }
    curr->data=a_code;
}

void huffman::compress() {
    createMinHeap();
    createTree();
    createCodes();
    saveEncodedFile();
}

void huffman::decompress() {
    getTree();
    saveDecodedFile();
}
