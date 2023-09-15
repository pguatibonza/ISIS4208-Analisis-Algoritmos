import heapq
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
             
def compress(text):
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
    
    return codewords,tree
    
"""
#TODO:

sistema de lectura de archivos
numero esperado de bits,
entropia
numero total de bits necesarios para guardar el texto
"""


compress("asjdajdjasjdajsdajdjpppqow")

