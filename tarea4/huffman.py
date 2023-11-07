import heapq
import heapq
from collections import Counter
import math


def calcular_entropia_peor_caso(frequencies):
    cantElemns = len(frequencies)
    return math.log(cantElemns,2)

def bitsEsperados(frecuencias, tamanio_texto, codewords):

    suma=0
    for letra,palabra in codewords.items():
        probabilidad= frecuencias[letra]/tamanio_texto
        suma+=probabilidad*len(palabra)
    return suma

def calcular_numero_total_bits_original(texto, codigos_huffman):
    num_bits_texto_original = sum(len(codigos_huffman[caracter]) for caracter in texto)
    return num_bits_texto_original

#Representa un nodo en el arbol de huffman
class Node: 
    def __init__(self, character, frecuency):
        self.character = character
        self.frecuency = frecuency
        self.left = None
        self.right = None

    def __lt__(self, otro):
        return self.frecuency < otro.frecuency

def huffman_tree(frequencies):
    #Priority queue for keeping the nodes sorted
    priority_queue=[Node(character,frecuency)for character, frecuency in frequencies.items()]
    heapq.heapify(priority_queue)

    #Building the huffman tree
    while(len(priority_queue)>1):
        node1=heapq.heappop(priority_queue)
        node2=heapq.heappop(priority_queue)

        new_node=Node(None,node1.frecuency+node2.frecuency)
        new_node.left=node1
        new_node.right=node2

        heapq.heappush(priority_queue,new_node)

    return priority_queue[0]

def code_words(huffman_tree:Node,current="", codewords ={}):
    if huffman_tree.character is not None:
        codewords[huffman_tree.character]=current
    if huffman_tree.left is not None:
        code_words(huffman_tree.left,current+"0",codewords)
    if huffman_tree.right is not None:
        code_words(huffman_tree.right,current+"1",codewords)
    return codewords
             
def compress():
    text = readF()
    frequencies={}
    #counting the frequency of each character
    for char in text:
        if char in frequencies:
            frequencies[char]+=1
        else:
            frequencies[char]=1

    tree= huffman_tree(frequencies)

    #Represents the code words for each character
    codewords=code_words(tree)
    
    
    
    print("Codigos de Huffman:")
    for letter, code in codewords.items():
        frecuency = frequencies[letter]
        print(f"{letter}: Codificación = {code}, Longitud = {len(code)}, Frecuencia = {frecuency:.4f}")
    
    entropia_peor_caso = calcular_entropia_peor_caso(frequencies)
    num_bits_texto_original = calcular_numero_total_bits_original(text, codewords)
    bits_esperados = bitsEsperados(frequencies, len(text), codewords)
    
    print(f"Número de bits esperados: {entropia_peor_caso}")
    print(f"Entropía en el peor de los casos: {bits_esperados:.4f}")
    print(f"Número total de bits para guardar el texto original: {num_bits_texto_original}")
    print()

    
    with open('outputHuffman.txt', 'w') as f:
        for val in codewords:
            f.write(val + "=" + codewords[val]+"\n")
    return codewords,tree

def readF():
    val = ""
    with open('inputHuffman.txt','r') as file:
        for line in file:
            val+=line
            
    val=val.replace("\n","")
    return val
compress()


