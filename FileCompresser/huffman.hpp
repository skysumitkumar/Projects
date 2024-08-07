//The #ifdef and #define directives are commonly used in C++ to prevent multiple inclusions of the same header file, which can cause errors. This is known as an "include guard."
#ifndef HUFFMAN_HPP
#define HUFFMAN_HPP
#include<string>
#include<vector>
#include<queue>
#include<fstream>
#include<iostream>
using namespace std;
//Define Huffman Tree Node
class Node
{
    public:
    char data;
    unsigned freq;
    string code;
    Node* left;
    Node* right;
    Node(char data='\0',unsigned freq=0):data(data),freq(freq),left(nullptr),right(nullptr) {}
};
class huffman
{
    private:
    vector<Node*>arr;
    fstream inFile;
    fstream outFile;
    Node* root;
    string inFileName;
    string outFileName;
    class Compare
    {
        public:
        bool operator()(Node* l,Node* r)
        {
            return l->freq > r->freq;
        }
    };
    //make a minHeap so to get every 2 small frequency nodes and pass compare that is our class that compare the frequency of every node while pushing the data in priority_queue
    priority_queue<Node*,vector<Node*>,Compare>minHeap;
    //Initializing a vector of tree nodes representing character's ascii value and initializing its frequency with 0
    void createArr();
    //creating min heap of nodes by frequency of characters in the input file
    void createMinHeap();
    //constructing the huffman tree
    void createTree();
    //Traversing the constructed tree to generate huffman codes of each present character
    void traverse(Node* ,string);
    //generating huffman codes
    void createCodes();
    //Function to convert a decimal number to its equivalent binary string
    string decToBin(int);
    //Function to convert binary string to its equivalent decimal value
    int binToDec(string);
    //saving huffman encoded file
    void saveEncodedFile();
    //saving decoded file to obtain the original file
    void saveDecodedFile();
    //Reading the file to reconstruct the huffman tree
    void getTree();
    //Reconstructing the huffman tree while decoding the file
    void buildTree(char,string&);
    public:
    //constructor
    huffman(string inFileName,string outFileName):inFileName(inFileName),outFileName(outFileName),root(nullptr)
    {
        createArr();
    }
    //compressing input file
    void compress();
    //Decompressing input file
    void decompress();
};
//The #endif directive in C++ is used to close a conditional preprocessor directive that starts with #if, #ifdef, or #ifndef. Here is a brief explanation of how these work together:
//#if / #endif: Used to include code only if a certain condition is true.
//#ifdef / #endif: Used to include code only if a certain macro is defined.
//#ifndef / #endif: Used to include code only if a certain macro is not defined.
#endif