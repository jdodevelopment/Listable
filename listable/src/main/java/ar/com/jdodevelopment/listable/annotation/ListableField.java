/*
    Copyright (c) 2019 Juan Daniel Ornella <juan.daniel.sp@gmail.com>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package ar.com.jdodevelopment.listable.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ar.com.jdodevelopment.listable.formatter.ListableFormatter;
import ar.com.jdodevelopment.listable.formatter.ToStringFormatter;

import static java.lang.annotation.ElementType.FIELD;

/**
 *
 * Indicates the fields of a class that are going to be shown in the list and in what way.
 *
 * @see ListableEntity
 * @author Juan Daniel Ornella
 */
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListableField {


    /**
     * Define the class that will format the values of the field.
     *
     * @return class of the formatter.
     */
    Class<? extends ListableFormatter> formatter() default ToStringFormatter.class;

    /**
     *
     * @return the id of the view where the value will be setted.
     */
    int viewResource() default android.R.id.text1;

}