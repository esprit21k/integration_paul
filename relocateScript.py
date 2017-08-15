import os, fnmatch
from shutil import copyfile

def find(pattern, path):
    result = []
    for root, dirs, files in os.walk(path):
        for name in files:
            if fnmatch.fnmatch(name, pattern):
                result.append(os.path.join(root, name))
    return result
root = os.getcwd()
srcfolder = root + "/src/main/frontend/build/static"
staticfolder = root + "/target/classes/public/static"
ftomake = ["/js", "/css"] 
for x in ftomake:
    if not os.path.exists(staticfolder + x):
        os.makedirs(staticfolder+x)

jsfiles = find("*.js", srcfolder + '/js')
copyfile(jsfiles[0], staticfolder+ '/js/bundle.js')


cssfile = find("*.css", srcfolder + '/css')
name = cssfile[0].split("/")[-1:][0]
full = (staticfolder+'/css/main.css')
copyfile(cssfile[0], full)

