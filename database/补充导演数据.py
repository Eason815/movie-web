import requests
import pymysql
import time
from lxml import etree
import re
import random

# 第一页0 第二页25 ... 225
conn = pymysql.connect(host="localhost", port=3306, user="root", password="123456", database="moviedb")
furl = "https://movie.douban.com/top250?start=%d"
headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"
}

movie_id = 0

with conn.cursor() as cursor:
    query = "select movie_id from movie"
    cursor.execute(query)
    movie_ids = cursor.fetchall()
    movie_ids = [id[0] for id in movie_ids]

    for i in range(0, 10):
        url = furl % (i * 25)
        resp = requests.get(url, headers=headers)
        resp.encoding = resp.apparent_encoding
        tree = etree.HTML(resp.text)
        urls = tree.xpath('//div[@class="item"]//div[@class="hd"]/a/@href')

        #详情页
        for url in urls:
            movie_id += 1
            if movie_id not in movie_ids:
                continue


            resp = requests.get(url, headers=headers)
            resp.encoding = resp.apparent_encoding
            tree = etree.HTML(resp.text)
            director = tree.xpath('//span[@class="attrs"]/a[@rel="v:directedBy"]/text()')[0]

            query = "select staff_id from staff where name = %s and is_director = 1"
            cursor.execute(query, director)

            #不存在这个staff，先创建
            if cursor.fetchone() == None:
                sql = "insert into staff(name, is_director) values(%s, %s)"
                cursor.execute(sql, (director, "1"))
                conn.commit()
            query = "select staff_id from staff where name = %s and is_director = 1"
            cursor.execute(query, director)
            staff_id = cursor.fetchone()[0]

            sql = "insert into movie_staff(movie_id, staff_id) values(%s, %s)"
            cursor.execute(sql, (str(movie_id), str(staff_id)))
            conn.commit()
                

conn.close()