/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MiasTestModule } from '../../../test.module';
import { DrugOrderDetailComponent } from '../../../../../../main/webapp/app/entities/drug-order/drug-order-detail.component';
import { DrugOrderService } from '../../../../../../main/webapp/app/entities/drug-order/drug-order.service';
import { DrugOrder } from '../../../../../../main/webapp/app/entities/drug-order/drug-order.model';

describe('Component Tests', () => {

    describe('DrugOrder Management Detail Component', () => {
        let comp: DrugOrderDetailComponent;
        let fixture: ComponentFixture<DrugOrderDetailComponent>;
        let service: DrugOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [DrugOrderDetailComponent],
                providers: [
                    DrugOrderService
                ]
            })
            .overrideTemplate(DrugOrderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DrugOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DrugOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DrugOrder(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.drugOrder).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
