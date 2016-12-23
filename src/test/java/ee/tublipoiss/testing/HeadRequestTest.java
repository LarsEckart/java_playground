/*
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

package ee.tublipoiss.testing;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;

public class HeadRequestTest {

    @Test
    public void should_not_throw() throws Exception {
        // given
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .head()
                .url(mockWebServer.url("/"))
                .build();

        final String responseBody = "{\"hello\":\"world\"}";
        final int length = responseBody.getBytes().length;
        final MockResponse mockResponseWithBody = new MockResponse().clearHeaders().setResponseCode(200)
                                                                       .setHeader("Content-Length", length)
                                                                       .setHeader("Content-Type", "application/json")
                                                                       .setBody(responseBody);
        mockWebServer.enqueue(mockResponseWithBody);

        final Response response = okHttpClient.newCall(request).execute();
        System.out.println(response.header("content-length"));
        System.out.println(response.body().string());
        // when

        // then

    }
}
