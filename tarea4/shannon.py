import math

def calcular_numero_total_bits_original(texto, codewords):
    num_bits_texto_original = sum(len(codewords[caracter]) for caracter in texto)
    return num_bits_texto_original

def calcular_entropia_peor_caso(frequencies):
    cantElemns = len(frequencies)
    return math.log(cantElemns,2)

def bits_esperados(frecuencias, tamanio_texto):

    suma=0
    for letra in frecuencias:
        probabilidad= frecuencias[letra]/tamanio_texto
        suma+=probabilidad*(-math.log(probabilidad,2))
    return suma

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

def readF():
    val = ""
    with open('inputShannon.txt','r') as file:
        for line in file:
            val+=line
            
    val=val.replace("\n","")
    return val




def compress():
    text=readF()
    frequencies={}
    #counting the frequency of each character
    for char in text:
        if char in frequencies:
            frequencies[char]+=1
        else:
            frequencies[char]=1
    old_frequencies=frequencies.copy()
    frequencies=sorted(frequencies.items(),key=lambda x:x[1],reverse=True)
    print(old_frequencies)

    codewords={}
    for letter,frecuency in frequencies:
        codewords[letter]=''

    codewords=shannon_fano(codewords,frequencies)



    print("Codigos de Shannon-Fano:")
    for letra, codigo in codewords.items():
        frequency = old_frequencies[letra]
        print(f"{letra}: Codificación = {codigo}, Longitud = {len(codigo)}, Frecuencia= {frequency:.4f}")

    entropia_peor_caso=calcular_entropia_peor_caso(old_frequencies)
    num_bits_texto_original=calcular_numero_total_bits_original(text,codewords)
    num_bits_esperados=bits_esperados(old_frequencies,len(text))
    
    print(f"Número de bits esperados: {num_bits_esperados}")
    print(f"Entropía en el peor de los casos: {entropia_peor_caso:.4f}")
    print(f"Número total de bits para guardar el texto original: {num_bits_texto_original:.2f}")
    print("\n")

    


    with open('outputShannon.txt', 'w') as f:
        for val in codewords:
            f.write(val + "=" + codewords[val]+"\n")
    return codewords



compress()





