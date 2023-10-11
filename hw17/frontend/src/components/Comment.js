import {Button, Col, Row} from "antd";
import React, {useState} from "react";

const Comment = ({comment, editComment, deleteComment}) => {
    const [value, setValue] = useState(comment.comment);

    return (
        <Col span={24}>
            <Row gutter={24}>
                <Col>
                    <input
                        value={value}
                        onChange={(e) => {
                            setValue(e.target.value)
                        }}
                    />
                </Col>
                <Col>
                    <Button onClick={() => editComment({
                        ...comment,
                        comment: value
                    })}>
                        Сохранить
                    </Button>
                </Col>
                <Col>
                    <Button onClick={() => deleteComment(comment.id)} danger type={'primary'}>
                        Удалить
                    </Button>
                </Col>
            </Row>
        </Col>
    )
}

export default Comment;