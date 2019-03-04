
class Vector<ElementType> {
    private arr: ElementType[];
    private size: number;

    constructor(count: number) {
        this.arr = new Array(count);
        this.size = 0;
    }

    public get length() {
        return this.size;
    }

    public get capacity() {
        return this.arr.length
    }

    public push(elem) {
        this.arr[this.size] = elem;
        this.size++;
    }

    public getItem(index: number) {
        if (index < 0 || index >= this.size) return undefined;
        return this.arr[index];
    }

    public pop() {
        this.size--;
        return this.arr[this.size];
    }

    public *[Symbol.iterator]() {
        for (let i = 0; i < this.size; i++) {
            yield this.arr[i];
        }
    }
}


let vec = new Vector<number>(10);
vec.push(1);
vec.push(25);
vec.push(15);
for (let i = 0; i < vec.length; i++)
    console.log(vec.getItem(i));

console.log("Before (let x of vec) loop");
for (let x of vec) {
    console.log("Top of (let x of vec) loop");
    console.log(x);
    console.log("Bottom of (let x of vec) loop")
}

console.log("After (let x of vec) loop");