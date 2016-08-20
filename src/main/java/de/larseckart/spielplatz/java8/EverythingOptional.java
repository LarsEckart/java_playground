/*
 * File: de.larseckart.spielplatz.java8.EverythingOptional
 * Created: 11:36 25/03/2016
 * Author: Lars Eckart <lars.eckart@fortumo.com>
 *
 * Copyright (C) Fortumo OÜ, 2016
 * All rights reserved. Proprietary and confidential.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 * All rights reserved. Proprietary and confidential.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Distribution, modification, reproduction, merging, publishing, sub-licensing,
 * sale and/or any other use (in binary form or otherwise) of this software is
 * not permitted, unless Fortumo OÜ has explicitly and in writing permitted such
 * use of the software.
 *
 * THIS SOFTWARE IS PROVIDED BY COPYRIGHT HOLDER ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Fortumo OÜ BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.larseckart.spielplatz.java8;

import java.util.Optional;

public class EverythingOptional {

    public static void main(String[] args) {

        Optional<String> optional = Optional.of("Lars on tubli poiss");

        String result = optional.orElse(getName());
        //String result = optional.orElseGet(() -> getName());

        System.out.println(result);

    }

    private static String getName() {
        System.out.println("computing");
        return "text";
    }

    private static Authentication findAuthentication(long id) {
        return new Authentication();
    }

    private Optional<Authentication> fixndAuthentication(long id) {
        return Optional.empty();
    }

    static class Authentication {

        public String getStatus() {
            return "";
        }
    }
}
