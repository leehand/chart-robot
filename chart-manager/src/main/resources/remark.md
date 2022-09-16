##删除问答数据测试index
delete /question_answer_robot_test

##创建问答数据测试index

PUT /question_answer_robot_test
{
	"mappings": {
		"properties": {
			"question": {
           "type":  "text",
          "analyzer": "ik_max_word",
		 "search_analyzer": "ik_max_word"
        },
        "similar_names": {
          "type":  "text",
          "analyzer": "ik_max_word",
					"search_analyzer": "ik_max_word"
        },
			"q_id": {
				"type": "text",
				"fields": {
					"keyword": {
						"type": "keyword",
						"ignore_above": 1256
					}
				}
			}
		}
	}
}



##添加一条数据
PUT /question_answer_robot_test/_doc/2423
{
  "question":"工资什么时候发呢？会发工资条吗？",
  "similar_names":["工资什么时候发呢？会发工资条吗？","会发工资条吗？"],
  "q_id":"21423455778976543"
}




GET /question_answer_robot_test/_search
{
  "query": {
    "match_all": {}
  }
}



##获取每个问题的相识度分值 
GET /question_answer_robot_test/_search
{
  "query": {
    "multi_match": {
      "query": "机构经营有效期如何填写",
      "fields": [
        "question",
        "similar_names"
      ]
    }
  }
}

