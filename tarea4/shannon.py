
def compress(text):
    frequencies={}
    #counting the frequency of each character
    for char in text:
        if char in frequencies:
            frequencies[char]+=1
        else:
            frequencies[char]=1

    #sorting the characters according to their frequency
    frequencies=sorted(frequencies.items(),key=lambda x:x[1],reverse=True)
    print(frequencies)

    codewords={}
    for letter,frecuency in frequencies:
        codewords[letter]=''

    codewords=shannon_fano(codewords,frequencies)
    print(codewords)
    return codewords


    
def shannon_fano(codewords,frecuencies):

    length=sum(tuple[1] for tuple in frecuencies)
    half=round(length/2)
    
    if len(frecuencies)>1:
        flag=0
        i=0
        #identifies the characters that will be encoded with 0
        for letter,frecuency in frecuencies:
            flag+=frecuency
            i+=1
            if flag>=half:
                break
        #assigns the codewords
        for j in range(i):
            codewords[frecuencies[j][0]]+='0'
        for j in range(i,len(frecuencies)):
            codewords[frecuencies[j][0]]+='1'
        #recursion
        codewords=shannon_fano(codewords,frecuencies[:i])
        codewords=shannon_fano(codewords,frecuencies[i:])
        return codewords
    else:
        return codewords

"""
#TODO:
sistema de lectura de archivos
numero esperado de bits,
entropia
numero total de bits necesarios para guardar el texto
"""


compress("hola mundoooopppasdasdas")