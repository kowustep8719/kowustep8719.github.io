/*Programmer: Stephen Owusu-Agyekum
* Date: 2024-04-16
* Version: 6.13.4
* Description: This TypeScript interface defines the structure of a Trip object used in the Angular application.
* Course: CS465-Full Stack Development I
* School name: Southern New Hampshire University
*/

export interface Trip {
    // internal MongoDB primary key
    _id: string, 
    code: string,
    name: string,
    length: string,
    start: Date,
    resort: string,
    perPerson: string,
    image: string,
    description: string
   }