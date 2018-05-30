import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Procedure } from './procedure.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Procedure>;

@Injectable()
export class ProcedureService {

    private resourceUrl =  SERVER_API_URL + 'api/procedures';

    constructor(private http: HttpClient) { }

    create(procedure: Procedure): Observable<EntityResponseType> {
        const copy = this.convert(procedure);
        return this.http.post<Procedure>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(procedure: Procedure): Observable<EntityResponseType> {
        const copy = this.convert(procedure);
        return this.http.put<Procedure>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Procedure>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Procedure[]>> {
        const options = createRequestOption(req);
        return this.http.get<Procedure[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Procedure[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Procedure = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Procedure[]>): HttpResponse<Procedure[]> {
        const jsonResponse: Procedure[] = res.body;
        const body: Procedure[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Procedure.
     */
    private convertItemFromServer(procedure: Procedure): Procedure {
        const copy: Procedure = Object.assign({}, procedure);
        return copy;
    }

    /**
     * Convert a Procedure to a JSON which can be sent to the server.
     */
    private convert(procedure: Procedure): Procedure {
        const copy: Procedure = Object.assign({}, procedure);
        return copy;
    }
}
