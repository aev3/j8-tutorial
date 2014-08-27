package org.awolart.j8.aggregates;

/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.List;

import org.awolart.j8.models.Person;


public class BulkDataOperations {

    public static void main(String... args) {

        // Create sample data

        List<Person> roster = Person.createRoster();

        // 1. Print names of members, for-each loop

        System.out.println("Members of the collection (for-each loop):");
        for (Person p : roster) {
            System.out.println(p.getName());
        }

        // 2. Print names of members, forEach operation

        System.out.println("Members of the collection (bulk data operations):");
        roster
                .stream()
                .forEach(e -> System.out.println(e.getName()));

        // 3. Print names of male members, forEach operation

        System.out.println(
                "Male members of the collection (bulk data operations):");
        roster
                .stream()
                .filter(e -> e.getGender() == Person.Sex.MALE)
                .forEach(e -> System.out.println(e.getName()));

        // 4. Print names of male members, for-each loop

        System.out.println("Male members of the collection (for-each loop):");
        for (Person p : roster) {
            if (p.getGender() == Person.Sex.MALE) {
                System.out.println(p.getName());
            }
        }

        // 5. Get average age of male members of the collection:
        /**
         The mapToInt operation returns a new stream of type IntStream (which is a stream that contains only integer values).
         The operation applies the function specified in its parameter to each element in a particular stream.
         In this example, the function is Person::getAge, which is a method reference that returns the age of the member. (Alternatively, you could use the lambda expression e -> e.getAge().) Consequently, the mapToInt operation in this example returns a stream that contains the ages of all male members in the collection roster.

         The average operation calculates the average value of the elements contained in a stream of type IntStream.
         It returns an object of type OptionalDouble. If the stream contains no elements, then the average operation
         returns an empty instance of OptionalDouble, and invoking the method getAsDouble throws a NoSuchElementException.
         The JDK contains many terminal operations such as average that return one value by combining the contents of a
         stream. These operations are called reduction operations; see the section Reduction for more information.

         Differences Between Aggregate Operations and Iterators

         Aggregate operations, like forEach, appear to be like iterators. However, they have several fundamental differences:

         They use internal iteration: Aggregate operations do not contain a method like next to instruct them to process the next element of the collection. With internal delegation, your application determines what collection it iterates, but the JDK determines how to iterate the collection. With external iteration, your application determines both what collection it iterates and how it iterates it. However, external iteration can only iterate over the elements of a collection sequentially. Internal iteration does not have this limitation. It can more easily take advantage of parallel computing, which involves dividing a problem into subproblems, solving those problems simultaneously, and then combining the results of the solutions to the subproblems. See the section Parallelism for more information.

         They process elements from a stream: Aggregate operations process elements from a stream, not directly from a collection. Consequently, they are also called stream operations.

         They support behavior as parameters: You can specify lambda expressions as parameters for most aggregate operations. This enables you to customize the behavior of a particular aggregate operation.
         */

        double average = roster
                .stream()
                .filter(p -> p.getGender() == Person.Sex.MALE)
                .mapToInt(Person::getAge)
                .average()
                .getAsDouble();

        System.out.println(
                "Average age of male members (bulk data operations): " +
                        average);
    }
}