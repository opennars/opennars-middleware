/*
 * The MIT License
 *
 * Copyright 2019 OpenNARS.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.opennars.middle.redisinput;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class Redis {
    //connection
    public static String redishost = "";
    public static int redisport = 0;
    public static String redispwd = "";
    public static void setupRedis(String redishost, int redisport, String redispwd) {
        Redis.redishost = redishost;
        Redis.redisport = redisport;
        Redis.redispwd = redispwd;
    }
    
    //Instance to use Redis
    public static Jedis r = null;
    public String queueName = "";
    public static Jedis getRedisConnection() {
        if(r != null) { //only one connection supported for now
            return r;   //though queues can differ in the particular plugins
        }
        JedisPool pool = new JedisPool(redishost, redisport);
        r = pool.getResource();
        if(!redispwd.isEmpty()) {
            try {
                r.auth(redispwd);
                r.connect();
            } catch(Exception ex) {
                System.out.println("Invalid password " + ex.toString());
            }
        }
        return r;
    }
}
