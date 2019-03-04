const fs = require('fs');
const path = "input.txt";

function readSavedFile(filePath) {
	return new Promise((resolve, reject) => {
		if (!filePath) throw "No file exists.";
		
		fs.readFile(filePath, "utf-8", (err, data) => {
			if(err) {
				return reject("No such readable file.");
			}
			return resolve(data);
		});
	});
}


function writeNewFile(filePath, fileData) {
	return new Promise((resolve, reject) => {
		if (!filePath) {
			throw "No writable path to text file provided.";
		}
			
		else if (!fileData){
			throw "No writable data text file provided";
		}
		
		fs.writeFile(filePath, fileData, (err, write) => ({
			if (err)
				{return reject(write);}
			
			return fileData;
		})
	});
}

var CaesarCipher = function(str){
	//if(amount<0){
		//return CaesarCipher(str,amount+26);
	//}

	var output=' ';
	var key=5;
	for(var i=0 ; i<str.length ; i++){	
		
		var x=string[i];
		for (var j = 0; j < 3; j++) {
			if(x.match(/[a-zA-Z]/i)){

			var xToNum=x.charCodeAt(0);

			if((xToNum>=97)&&(xToNum<=122)){
				xToNum-=key;
				xToNum=(xToNum-97)%26+97;
			}	

			else if((xToNum>=65)&&(xToNum<=90)){
				xToNum-=key;
				xToNum=(xToNum-65)%26+65;
			}	

			var decode=String.fromCharCode(xToNum);
			output=decode;
		    }
		}

		key+=2;	

	    
	}

	return output;
}

fs.exist (path, (exists) => {
	if (exists) {
		readSavedFile(path).then((data) => {
			
			CaesarCipher(data);

			writeNewFile("output.txt");
			
		}, (err) => {
			console.log(err);
		});
	}
})















