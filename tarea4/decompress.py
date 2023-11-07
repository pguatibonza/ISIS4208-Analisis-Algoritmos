def readText():
    val = ""
    dic = {}
    flag = True
    with open('compressOutput.txt','r') as file:
        for line in file:
            if line == "######\n":
                flag = False
                continue
            if flag:
                val+=line
            else:
                text = line.split("=")
                dic[text[0]] = text[1][:-1]
            
            
    val=val.replace("\n","")
    return dic,val

def writeText(text):
    with open('decompressOutput.txt', 'w') as f:
        f.write(text)

def main():
    dic, text = readText()
    print(dic)
    print(text)
    cant=0
    while "1" in text or "0" in text:
        for val in dic:
            if text[cant:].startswith(dic[val]):
                text=text.replace(dic[val], val, 1)
                cant+=1
                print(text)
    writeText(text)
    
main()