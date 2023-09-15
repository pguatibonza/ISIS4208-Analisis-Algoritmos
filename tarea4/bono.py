import huffman as huffman 
def unzip(tree:huffman.Node,coded_text):
    code=""
    actual_node=tree
    for bit in coded_text:
        if tree.character is not None:
            code+=tree.character
            actual_node=tree
        if bit=="0":
            actual_node=tree.left
        if bit=="1":
            actual_node=tree.right
    return code

def zip(text):
    codewords,tree=huffman.compress(text)

    #Code the text
    coded_text = "".join([codewords[character] for character in text])

    return coded_text,tree

""""
#TODO:
SISTEMA DE LECTURA DE ARCHIVOS
PROBARLO
"""


