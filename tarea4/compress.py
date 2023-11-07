def readHuffman():
    dic={}
    codes = ""
    with open('outputHuffman.txt','r') as file:
        for line in file:
            text = line.split("=")
            dic[text[0]] = text[1][:-1]
            codes+=line
            
    return dic,codes

def readText():
    val = ""
    with open('compressText.txt','r') as file:
        for line in file:
            val+=line
            
    val=val.replace("\n","")
    return val
    
def main():
    dic,codes = readHuffman()
    result = readText()
    print(dic)
    print(result)
    print("")
    for elem in dic:
        result = result.replace(elem, dic[elem])
    print(result)
    print(codes)
    with open('compressOutput.txt', 'w') as f:
        f.write(result)
        f.write("\n")
        f.write("######")
        f.write("\n")
        f.write(codes)
    
main()